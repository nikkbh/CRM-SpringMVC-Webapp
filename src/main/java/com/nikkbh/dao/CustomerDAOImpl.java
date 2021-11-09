package com.nikkbh.dao;

import com.nikkbh.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO{

    // need to inject session factory
    @Autowired
    private SessionFactory sessionFactory;

    // implement the method
    @Override
    public List<Customer> getCustomers() {

        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query.. sorty by last name
        Query<Customer> theQuery =  session.createQuery("from Customer order by lastName", Customer.class);

        // Execute the query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return the result list
        return customers;
    }

    @Override
    public Customer getCustomer(int customerId) {

        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // get customer using the customer id
        Customer customer = session.get(Customer.class, customerId);

        // return the customer

        return customer;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // save/update the customer to db
        currentSession.saveOrUpdate(theCustomer);

    }

    @Override
    public void deleteCustomer(int customerId) {

        // get current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // get customer by id
        Customer theCustomer = session.get(Customer.class, customerId);

        // delete the customer by id from db
        session.delete(theCustomer);

        // --------Alternate way to delete-----------------------

//        Query theQuery = session.createQuery("delete  from Customer where id=:customerId");
//
//        theQuery.setParameter("customerId", customerId);
//
//        theQuery.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        // get current hibernate session
        Session session = sessionFactory.getCurrentSession();

        Query theQuery = null;

        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery =session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =session.createQuery("from Customer", Customer.class);
        }

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return the results
        return customers;
    }
}
