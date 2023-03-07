import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Reader;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    static SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Shopper.class)
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    static Scanner scanner = new Scanner(System.in);

    static String successful = "Command executed successfully";
    public static void main(String args[]) {



        mainMenu();
        factory.close();
    }
    public static void mainMenu() {
        String query = scanner.next();
        if(query.equals("0") || query.equals("/exit") || query.equals("/quit")) {
            return;
        }
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        if(query.equals("/showProductsByPerson")) {
            showProducts(session);
        }
        else if (query.equals("/findPersonsByProductTitle")) {
            findPersons(session);
        }
        else if(query.equals("/removePerson")) {
            removePerson(session);
        }
        else if(query.equals("/removeProduct")) {
            removeProduct(session);
        }
        else if(query.equals("/showPersons")) {
            showAllPersons(session);
        }
        else if(query.equals("/showProducts")) {
            showAllProducts(session);
        }
        else if(query.equals("/addPerson")) {
            addPerson(session);
        }
        else if(query.equals("/addProduct")) {
            addProduct(session);
        }
        else if(query.equals("/buy")) {
            buy(session);
        }
        else {
            System.out.println("Uncorrect command");
        }
        session.getTransaction().commit();
        mainMenu();
    }
    public static void showAllPersons(Session session) {

        String name= scanner.nextLine().trim();
        List<Shopper> list = session.createQuery("from Shopper").getResultList();
        for(Shopper shopper : list) {
            System.out.println(shopper);
        }
        System.out.println(successful);
    }
    public static void showAllProducts(Session session) {

        String name= scanner.nextLine().trim();
        List<Product> list = session.createQuery("from Product").getResultList();
        for(Product product : list) {
            System.out.println(product);
        }
        System.out.println(successful);
    }
    public static void addPerson(Session session) {
        System.out.println("Enter name: ");
        String name= scanner.nextLine().trim();
        session.save(new Shopper(name));
        System.out.println(successful);
    }
    public static void addProduct(Session session) {
        System.out.println("Enter name and price:");
        scanner.nextLine();
        String name= scanner.nextLine().trim();
        long price = scanner.nextLong();
        scanner.nextLine();
        session.save(new Product(name,price));
        System.out.println(successful);
    }
    public static void showProducts(Session session) {
        String name= scanner.nextLine().trim();
        List<Shopper> list = session.createQuery("from Shopper where name = :name").setParameter("name",name).getResultList();
        for(Shopper shopper : list) {
            List<Product> products = shopper.getProducts();
            for(Product product : products) {
                System.out.println(product);
            }
        }
        System.out.println(successful);
    }
    public static void findPersons(Session session) {
        String name = scanner.nextLine().trim();
        List<Product> list = session.createQuery("from Product where name = :name").setParameter("name",name).getResultList();
        for(Product product : list) {
            List<Shopper> shoppers = product.getShoppers();
            for(Shopper shopper : shoppers) {
                System.out.println(shopper);
            }
        }
        System.out.println(successful);
    }
    public static void removePerson(Session session) {
        String name = scanner.nextLine().trim();
        List<Shopper> list = session.createQuery("from Shopper where name = :name").setParameter("name",name).getResultList();
        for(Shopper shopper : list) {
            session.remove(shopper);
        }
        System.out.println(successful);
    }
    public static void removeProduct(Session session) {
        String name = scanner.nextLine().trim();
        List<Product> list = session.createQuery("from Product where name = :name").setParameter("name",name).getResultList();
        for(Product product : list) {
            session.remove(product);
        }
        System.out.println(successful);
    }
    public static void buy(Session session) {
        System.out.println("Enter name and name of product:");
        scanner.nextLine();
        String name1 = scanner.nextLine().trim();
        String name2 = scanner.nextLine().trim();
        List<Shopper> shopperlist = session.createQuery("from Shopper where name = :name").setParameter("name",name1).getResultList();
        List<Product> productlist = session.createQuery("from Product where name = :name").setParameter("name",name2).getResultList();
        for(Product product : productlist) {
            for(Shopper shopper : shopperlist) {
                product.getShoppers().add(shopper);
            }
        }
        System.out.println(successful);

    }
}
