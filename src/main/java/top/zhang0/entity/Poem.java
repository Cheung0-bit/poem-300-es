package top.zhang0.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <诗>
 *
 * @Author cheung0
 * @createTime 2022/10/2 20:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Poem {

    private String id;

    private String contents;

    private String type;

    private String author;

    private String title;

}
