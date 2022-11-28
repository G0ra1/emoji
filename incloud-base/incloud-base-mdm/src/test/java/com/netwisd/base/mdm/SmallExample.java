package com.netwisd.base.mdm;

import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.mdm.entity.MdmPost;
import com.netwisd.base.mdm.entity.MdmPostUser;
import com.netwisd.base.mdm.entity.MdmUser;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.driver.Session;
import org.neo4j.graphdb.*;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

public class SmallExample {


//    // Driver objects are thread-safe and are typically made available application-wide.
//    Driver driver;
//
//    public SmallExample(String uri, String user, String password)
//    {
//        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
//    }
//
//    private void addPerson(String name)
//    {
//        // Sessions are lightweight and disposable connection wrappers.
//        try (Session session = driver.session())
//        {
//            // Wrapping a Cypher Query in a Managed Transaction provides atomicity
//            // and makes handling errors much easier.
//            // Use `session.writeTransaction` for writes and `session.readTransaction` for reading data.
//            // These methods are also able to handle connection problems and transient errors using an automatic retry mechanism.
//            session.writeTransaction(tx -> tx.run("MERGE (a:Person {name: $x})", parameters("x", name)));
//        }
//    }
//
//    private void printPeople(String initial)
//    {
//        try (Session session = driver.session())
//        {
//            // A Managed transaction is a quick and easy way to wrap a Cypher Query.
//            // The `session.run` method will run the specified Query.
//            // This simpler method does not use any automatic retry mechanism.
//            Result result = session.run(
//                    "MATCH (a:Person) WHERE a.name STARTS WITH $x RETURN a.name AS name",
//                    parameters("x", initial));
//            // Each Cypher execution returns a stream of records.
//            while (result.hasNext())
//            {
//                Record record = result.next();
//                // Values can be extracted from a record by index or name.
//                System.out.println(record.get("name").asString());
//            }
//        }
//    }

//    public void close()
//    {
//        // Closing a driver immediately shuts down all open connections.
//        driver.close();
//    }

    public static void main(String... args)
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse("2018-01-12 17:07:05.0",df);
        System.out.println("--------------");
        System.out.println(ldt);

        //指定 Neo4j 存储路径
//        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(Paths.get("D:\\neo4j_databases\\neo4j")).build();
//        GraphDatabaseService graphDb = managementService.database("neo4j");
//        try(Transaction tx = graphDb.beginTx()){
//            Node user1 = tx.createNode(Label.label("AAA"));
//            user1.setProperty("name", "John Johnson");
//
//            Node user2 = tx.createNode(Label.label("AAA"));
//            user2.setProperty("name", "Kate Smith");
//
//            Node user3 = tx.createNode(Label.label("AAA"));
//            user3.setProperty("name", "Jack Jeffries");
//            tx.commit();
//        }

//        Transaction tx = graphDb.beginTx();
//        //查询数据库
//        String query ="match (n) return n.name";
//        Map<String, Object > parameters = new HashMap<String, Object>();
//        try ( org.neo4j.graphdb.Result result = tx.execute( query, parameters ) ) {
//            while ( result.hasNext() ) {
//                Map<String, Object> row = result.next();
//                for ( String key : result.columns() ) {
//                    System.out.printf( "%s = %s%n", key, row.get( key ) );
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Session session = NeoUtils.getSession();
//
//        session.writeTransaction(tx -> tx.run("MERGE (a:TT {name: $x})", parameters("x", "XHL")));
//        MdmUser mdmUser = new MdmUser();
//        mdmUser.setId(111L);
//        mdmUser.setUserName("mananhui");
//        mdmUser.setUserNameCh("马南辉");
//        mdmUser.setIdCard("3729221995");
//        List<MdmUser> ms = new ArrayList<>();
//        ms.add(mdmUser);
//        NeoUtils.saveUserNode(ms);
//
//        MdmUser mdmUser2 = new MdmUser();
//        mdmUser2.setId(222L);
//        mdmUser2.setUserName("JB");
//        mdmUser2.setUserNameCh("JB辉");
//        mdmUser2.setIdCard("3729221996");
//        //NeoUtils.saveUserNode(mdmUser2);
//
//        //NeoUtils.getNodeUser(mdmUser);
//        MdmPost mdmPost = new MdmPost();
//        mdmPost.setId(111L);
//        mdmPost.setPostCode("bumenzhuren");
//        mdmPost.setPostName("部门主任");
//        //NeoUtils.savePostNode(mdmPost);
//
//        MdmPost zjlMdmPost = new MdmPost();
//        zjlMdmPost.setId(333L);
//        zjlMdmPost.setPostCode("kuaiji");
//        zjlMdmPost.setPostName("会计");
//        MdmPost cnMdmPost = new MdmPost();
//        cnMdmPost.setId(444L);
//        cnMdmPost.setPostCode("chuna");
//        cnMdmPost.setPostName("出纳");
//        List<MdmPost> posts = new ArrayList<>();
//        posts.add(zjlMdmPost);
//        posts.add(cnMdmPost);
//        //NeoUtils.savePostNode(posts);
//
//        MdmPostUser mdmPostUser = new MdmPostUser();
//        mdmPostUser.setId(222L);
//        mdmPostUser.setIsMaster(0);
        //NeoUtils.mergeUserPostRel(mdmUser,zjlMdmPost,mdmPostUser);

        //先删除 mananhui的所有主岗信息 然后把主岗改为 总经理
        //NeoUtils.delUserPostRel(mdmUser, YesNo.YES.code);

        //NeoUtils.mergeUserPostRel(mdmUser,zjlMdmPost,mdmPostUser);










//        SmallExample example = new NeoUtils("bolt://192.168.124.144:7687", "neo4j", "Netwisd*8");
//        SmallExample example = new SmallExample("bolt://192.168.124.144:7687", "neo4j", "Netwisd*8");
//        example.addPerson("Ada");
//        example.addPerson("Alice");
//        example.addPerson("Bob");
//        example.printPeople("A");
//        example.close();
    }
}
