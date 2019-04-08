package CDiesel72;

import CDiesel72.Entity.CustomUser;
import CDiesel72.Entity.UserRole;
import CDiesel72.Entity.ZipFile;
import CDiesel72.Exception.FileDownErrorException;
import CDiesel72.Exception.FileDownNotFoundException;
import CDiesel72.Other.Path;
import CDiesel72.Other.SaveZip;
import CDiesel72.Service.UserService;
import CDiesel72.Service.ZipFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Controller
public class MyController {
    @Autowired
    private UserService userService;

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    private ZipFileService zipFileService;

    //static final int ITEMS_PER_PAGE = 6;
    private final String PATH_FILE = "D:/files/";

    private User getUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private CustomUser getCustomUser() {
        User user = getUser();

        String login = user.getUsername();
        return userService.findByLogin(login);
    }

    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

    @RequestMapping("/")
    public String index(Model model) {
        User user = getUser();

        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        boolean bAdm = isAdmin(user);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("admin", bAdm);
        if (bAdm) {
            model.addAttribute("files", zipFileService.findAll());
        } else {
            model.addAttribute("files", zipFileService.findAll(customUser));
        }

        return "index";
    }

    @RequestMapping("/page_update")
    public String updatePage(Model model) {
        User user = getUser();

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());

        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePass(
            @RequestParam(required = true, name = "old_pass") String oldPass,
            @RequestParam(required = true) String password,
            @RequestParam(required = true) String pass1,
            Model model) {

        System.out.println(1);
        User user = getUser();

        String login = user.getUsername();
        String passHashUser = userService.findByLogin(login).getPassword();
        //String passHashUser = user.getPassword();

        System.out.println(2);
        String oldPassHash = passwordEncoder.encodePassword(oldPass, null);

        if (!passHashUser.equals(oldPassHash)) {
            model.addAttribute("oldpass", true);
            model.addAttribute("login", login);
            model.addAttribute("roles", user.getAuthorities());
            return "update";
        }

        if (!pass1.equals(password)) {
            model.addAttribute("pass", true);
            model.addAttribute("login", login);
            model.addAttribute("roles", user.getAuthorities());
            return "update";
        }

        String passHash = passwordEncoder.encodePassword(password, null);

        userService.updateUser(login, passHash);

        return "redirect:/";
    }

    @RequestMapping(value = "/newuser/{adm}", method = RequestMethod.POST)
    public String newUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String pass1,
            @PathVariable("adm") String adm,
            Model model) {

        if (!pass1.equals(password)) {
            model.addAttribute("pass", true);
            model.addAttribute("login", login);
            return "register";
        }

        String passHash = passwordEncoder.encodePassword(password, null);

        if ("".equals(login) ||
                !userService.addUser(login, passHash, UserRole.USER)) {
            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            return "register";
        }

        if ("admin".equals(adm)) {
            return "redirect:/admin";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/delete_user", method = RequestMethod.POST)
    public String deleteUser(
            @RequestParam(name = "toDelete[]", required = false) long[] ids,
            Model model) {

        if (ids != null && ids.length > 0)
            userService.deleteUsers(ids);

        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register/{adm}")
    public String register(
            @PathVariable("adm") String adm,
            Model model) {

        if ("admin".equals(adm)) {
            model.addAttribute("adm", "admin");
        } else {
            model.addAttribute("adm", "no");
        }
        return "register";
    }

    @RequestMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    //Zip архивы

    @RequestMapping(value = "/add_file", method = RequestMethod.POST)
    public String addFile(@RequestParam("file_d") MultipartFile[] fileD,
                          @RequestParam(required = false, name = "name_z") String nameZ,
                          Model model) {

        if (fileD.length <= 0 || fileD.length > 5) {
            throw new FileDownErrorException();
        }

        CustomUser customUser = getCustomUser();
        long id = customUser.getId();

        String name = null;
        if (nameZ == null || "".equals(nameZ)) {
            name = "Archive_" + (new Date().getTime()) + ".zip";
        } else {
            name = nameZ + ".zip";
        }

        File zip = null;

        try {
            zip = Path.createFile(PATH_FILE + id + "/", name);


            zipFileService.addFile(new ZipFile(customUser, zip));

            SaveZip sz = new SaveZip();
            sz.start(zip, fileD);

        } catch (IOException e) {
            throw new FileDownErrorException();
        }

        return "redirect:/";
    }

    @RequestMapping("/file_get/{file_id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("file_id") long id) {
        return fileById(id);
    }

    @RequestMapping(value = "/delete_file", method = RequestMethod.POST)
    public String onDelete(
            @RequestParam(name = "toDelete[]", required = false) long[] ids) {

        try {
            for (long id : ids) {
                zipFileService.findOne(id).getFile().delete();
            }

            zipFileService.deleteFile(ids);

            return "redirect:/";
        } catch (Exception e) {
            throw new FileDownErrorException();
        }
    }

    private ResponseEntity<byte[]> fileById(long id) {
        File file = zipFileService.findOne(id).getFile();

        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            is.read(bytes);
            if (bytes == null)
                throw new FileDownNotFoundException();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            throw new FileDownNotFoundException();
        }
    }
}
