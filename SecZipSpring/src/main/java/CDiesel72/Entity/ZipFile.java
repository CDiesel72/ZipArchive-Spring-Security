package CDiesel72.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;

/**
 * Created by Diesel on 23.03.2019.
 */

@Entity
@Table(name = "zipfiles")
@Data
@NoArgsConstructor
public class ZipFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private File file;

    @ManyToOne
    @JoinColumn(name = "custom_user_id")
    private CustomUser customUser;

    public ZipFile(CustomUser customUser, File file) {
        this.customUser = customUser;
        this.file = file;
    }
}
