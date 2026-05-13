package view;

import java.util.Scanner;
import java.util.List;

public interface BaseView<T> {
    void insertList(List<T> list);
    List<T> getElementList();
    boolean searchByPK(String value);
    void showCRUD(Scanner entrada);
    void create(Scanner entrada);
    void update(Scanner entrada);
    void delete(Scanner entrada);
    void list(Scanner entrada);
    void read(Scanner entrada);
}