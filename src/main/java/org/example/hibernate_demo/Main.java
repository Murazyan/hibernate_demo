package org.example.hibernate_demo;

import org.example.hibernate_demo.models.Address;
import org.example.hibernate_demo.models.Article;
import org.example.hibernate_demo.models.User;
import org.example.hibernate_demo.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User poxos = User.builder()
                .name("Poxos")
                .surname("Poxosyan")
                .email("poxos@gmail.com")
                .password("123")
                .age(28)
                .build();
        User petros = User.builder()
                .name("Petros")
                .surname("Poxosyan")
                .email("poxos@gmail.com")
                .password("123")
                .age(28)
                .build();


        Integer id = (Integer) session.save(poxos);
        Integer petrosId = (Integer) session.save(petros);
        System.out.println("+++" + petrosId);
        poxos.setId(id);

        session.save(Article.builder()
                .content("Content 1")
                .title("Title 1")
                .author(poxos)
                .build()
        );

        Address address = Address.builder()
                .country("Armenia")
                .street("xxx")
                .address("yyyy")
                .build();
        Integer addressId = (Integer) session.save(address);
        System.out.println("saved address : " + address);
        session.getTransaction().commit();
        Query<User> q = session.createQuery(" from User", User.class);
        List<User> resultList = q.list();
        System.out.println("total users:" + resultList.size());

        for (User s : resultList) {
            System.out.println("User : " + s);
        }


        Query<Article> articleQuery = session.createQuery(" from Article", Article.class);
        List<Article> articles = articleQuery.list();

        System.out.println("total articles:" + articles.size());

        for (Article s : articles) {
            System.out.println("Article : " + s);
        }

        address.setId(addressId);
        poxos.setAddress(address);

        session.beginTransaction();
        session.save(poxos);
        session.getTransaction().commit();

        Query<User> qq = session.createQuery(" from User u where u.name='Poxos'", User.class);
        User re = qq.list().get(0);
        System.out.println("Poxos user :" + re);


    }
}
