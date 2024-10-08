/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mvc.modelo;

import java.util.List;

/**
 *
 * @author Alejandra
 */
public interface IDAO<T> {

    public void create(T t);

    public T read(int id);

    public void update(T t);

    public void delete(int id);

    public List<T> getAll();
}
