package top.zhang0.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.zhang0.entity.Poem;

import java.util.List;

/**
 * <>
 *
 * @Author cheung0
 * @createTime 2022/10/2 21:29
 */
@Mapper
public interface PoemDao {

    /**
     *
     * @return
     */
    @Select("select * from poem")
    List<Poem> queryAll();
}
