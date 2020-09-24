package com.main.schoolux.services;
import java.util.Collection;




// interface
public interface Service<T> {


    boolean alreadyExist(T t);

    T selectOneByIdOrNull(int id);

    Collection<T> selectAllOrNull();




    void insert(T t);

    void update(T t);

    void delete(T t);

    /*
    void deleteLogically (T t); // modifie le boolean field isActive d'un objet et call la méthode update (T t) qui fait le merge()

    On met cette methode dans l'interface courante dans le cas où toutes les tables ont un champ isActive afin de simuler une suppression logique sans supprimer réellement le record
    Si des tables ne l'ont pas, les services liées à ces tables seront obligé de définir la méthode qui ne servira jamais vu que tous les services implémentent l'interface courante

     */

}