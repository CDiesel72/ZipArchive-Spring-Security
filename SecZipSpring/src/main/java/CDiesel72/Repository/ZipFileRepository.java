package CDiesel72.Repository;

import CDiesel72.Entity.CustomUser;
import CDiesel72.Entity.ZipFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZipFileRepository extends JpaRepository<ZipFile, Long> {

    @Query("SELECT z FROM ZipFile z WHERE z.customUser = :customUser")
    List<ZipFile> findByCustomUser(@Param("customUser") CustomUser customUser);
}