package org.baeldung.mongotemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.baeldung.config.MongoConfig;
import org.baeldung.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class MongoTemplateQueryIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void testSetup() {
        mongoTemplate.createCollection(User.class);
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    public void givenUsersExist_whenFindingUserWithAgeLessThan50AndGreateThan20_thenUsersAreFound() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);
        user = new User();
        user.setName("Antony");
        user.setAge(55);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("age").lt(50).gt(20));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(1));
    }

    @Test
    public void givenUsersExist_whenFindingUserWithNameStartWithA_thenUsersAreFound() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^A"));

        List<User> users = mongoTemplate.find(query, User.class);
        assertThat(users.size(), is(2));
    }

    @Test
    public void givenUsersExist_whenFindingByPage_thenUsersAreFoundByPage() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        final Pageable pageableRequest = new PageRequest(0, 2);
        Query query = new Query();
        query.with(pageableRequest);

        List<User> users = mongoTemplate.find(query, User.class);
        assertThat(users.size(), is(2));
    }

    @Test
    public void givenUsersExist_whenFindingUsersAndSortThem_thenUsersAreFoundAndSorted() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "age"));

        List<User> users = mongoTemplate.find(query, User.class);
        assertThat(users.size(), is(3));
    }
}
