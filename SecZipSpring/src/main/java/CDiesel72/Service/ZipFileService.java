package CDiesel72.Service;

import CDiesel72.Entity.CustomUser;
import CDiesel72.Entity.ZipFile;
import CDiesel72.Repository.UserRepository;
import CDiesel72.Repository.ZipFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ZipFileService {
    @Autowired
    private ZipFileRepository zipFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addFile(ZipFile zipFile) {
        zipFileRepository.save(zipFile);
    }

    @Transactional
    public void deleteFile(long[] idList) {
        for (long id : idList)
            zipFileRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<ZipFile> findAll(Pageable pageable) {
        return zipFileRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<ZipFile> findAll() {
        return zipFileRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ZipFile> findAll(CustomUser customUser) {
        return zipFileRepository.findByCustomUser(customUser);
    }

    @Transactional(readOnly = true)
    public ZipFile findOne(long id) {
        return zipFileRepository.findOne(id);
    }
}
