package ci.imako.imakocrmtest.services;

import java.util.List;

public interface AbstractService<T, Long> {

    /**
     *
     * @return
     */
    List<T> findAll();

    /**
     *
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     *
     * @param t
     * @return
     */
    T save(T t);

    /**
     *
     * @param t
     */
    void update(T t);

    /**
     *
     * @param t
     */
    void delete(T t);

    /**
     *
     * @param id
     */
    void deleteById(Long id);
}
