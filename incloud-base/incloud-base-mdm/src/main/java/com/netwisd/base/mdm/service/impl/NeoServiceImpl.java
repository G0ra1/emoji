package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.netwisd.base.mdm.constants.NeoNodeEnum;
import com.netwisd.base.mdm.constants.NeoRelEnum;
import com.netwisd.base.mdm.entity.*;
import com.netwisd.base.mdm.service.NeoService;
import com.netwisd.common.core.exception.IncloudException;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

@Service
public class NeoServiceImpl implements NeoService {
//
//    @Value("${spring.neo4j.neo4juri}")
//    private String neo4juri;
//
//    @Value("${spring.neo4j.username}")
//    private String username;
//
//    @Value("${spring.neo4j.password}")
//    private String password;
//
//    // Driver objects are thread-safe and are typically made available application-wide.
//    private Driver driver;
//    private Session session;
//    public synchronized Session getSession() {
//        if(null == session) {
//            driver = GraphDatabase.driver(neo4juri, AuthTokens.basic(username, password));
//            session = driver.session();
//        }
//        return  session;
//    }
//
//    @Override
//    @Transactional
//    public boolean saveUserNode(List<MdmUser> mdmUsers) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            if(CollectionUtil.isNotEmpty(mdmUsers)) {
//                Transaction tx = session.beginTransaction();
//                for (MdmUser mdmUser : mdmUsers) {
//                    tx.run("CREATE (u:"+ NeoNodeEnum.USER.code+" {mid: $x0, userName: $x1, userNameCh: $x2, idCard: $x3})",
//                            parameters("x0",mdmUser.getId(),"x1",mdmUser.getUserName(),"x2",mdmUser.getUserNameCh(),"x3",mdmUser.getIdCard()));
//                }
//                tx.commit();
//            } else {
//                throw new IncloudException("没有人员相关数据！");
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public boolean getNodeUser(MdmUser mdmUser) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            Result result = session.run(
//                    "MATCH (u:"+ NeoNodeEnum.USER.code+") where u.mid=$x0 return u.name", parameters("x0",mdmUser.getId()));
//            while (result.hasNext()) {
//                Record record = result.next();
//                // Values can be extracted from a record by index or name.
//                System.out.println(record.get("u.userName").asString());
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean mergeUserPostRel(MdmUser mdmUser, MdmPost mdmPost, MdmPostUser mdmPostUser) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "MATCH (u:"+ NeoNodeEnum.USER.code+" {mid:$x0})," +
//                            "(p:"+ NeoNodeEnum.POST.code+" {mid:$x1})" +
//                            "MERGE (u)-[:"+ NeoRelEnum.USER_POST.code+" {mid:$x2, isMaster:$x3}]->(p) ", parameters("x0",mdmUser.getId(),"x1",mdmPost.getId(),"x2",mdmPostUser.getId(),"x3",mdmPostUser.getIsMaster())
//                    )
//            );
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean delUserPostRel(MdmUser mdmUser, Integer isMaster) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "MATCH (u)-[q:"+ NeoRelEnum.USER_POST.code+" {isMaster:$x0} ]->(p) where u.mid=$x1 delete q", parameters("x0",isMaster,"x1",mdmUser.getId())
//                    )
//            );
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean delUserPostRelById(Long id) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "MATCH (u:"+ NeoNodeEnum.USER.code+")-[q:"+ NeoRelEnum.USER_POST.code+" {mid:$x0} ]->(p:"+ NeoNodeEnum.POST.code+") delete q", parameters("x0",id)
//                    )
//            );
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean savePostNode(List<MdmPost> mdmPostList) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            if(CollectionUtil.isNotEmpty(mdmPostList)) {
//                Transaction tx = session.beginTransaction();
//                for (MdmPost mdmPost : mdmPostList) {
//                    tx.run("CREATE (u:"+ NeoNodeEnum.POST.code+" {mid: $x0, postName: $x1, postCode: $x2})",
//                            parameters("x0",mdmPost.getId(),"x1",mdmPost.getPostName(),"x2",mdmPost.getPostCode()));
//                }
//                tx.commit();
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean saveRoleNode(MdmRole mdmRole) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "CREATE (u:"+ NeoNodeEnum.ROLE.code+" {mid: $x0, roleCode: $x1, roleName: $x2, roleType: $x3})", parameters("x0",mdmRole.getId(),"x1",mdmRole.getRoleName(),"x2",mdmRole.getRoleCode(),"x3",mdmRole.getRoleType())));
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }
//        finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean mergeRoleRestRel(MdmRole mdmRole, List<MdmRoleResource> mdmRoleResourceList) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            if(CollectionUtil.isNotEmpty(mdmRoleResourceList)) {
//                Transaction tx = session.beginTransaction();
//                for (MdmRoleResource mdmRoleResource : mdmRoleResourceList) {
//                    tx.run("MATCH (ro:"+ NeoNodeEnum.ROLE.code+" {mid:$x0})," +
//                            "(re:"+ NeoNodeEnum.RES.code+" {mid:$x1})" +
//                            "MERGE (ro)-[:"+ NeoRelEnum.ROLE_RES.code+" {mid:$x2}]->(re) ", parameters("x0",mdmRole.getId(),"x1",mdmRoleResource.getResourceId(),"x2",mdmRoleResource.getId()));
//                }
//                tx.commit();
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean delRoleResRel(MdmRole mdmRole) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "MATCH (ro)-[q:"+ NeoRelEnum.ROLE_RES.code+"]->(re) where ro.mid=$x0 delete q", parameters("x0",mdmRole.getId()))
//            );
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean mergeRolePostRel(MdmRole mdmRole, List<MdmRolePost> mdmRolePosts) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            if(CollectionUtil.isNotEmpty(mdmRolePosts)) {
//                Transaction tx = session.beginTransaction();
//                for (MdmRolePost mdmRolePost : mdmRolePosts) {
//                    tx.run("MATCH (ro:"+ NeoNodeEnum.ROLE.code+" {mid:$x0})," +
//                            "(p:"+ NeoNodeEnum.POST.code+" {mid:$x1})" +
//                            "MERGE (ro)-[:"+ NeoRelEnum.ROLE_POST+" {mid:$x2}]->(p) ", parameters("x0",mdmRole.getId(),"x1",mdmRolePost.getPostId(),"x2",mdmRolePost.getId()));
//                }
//                tx.commit();
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean delRolePostRel(MdmRole mdmRole) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "MATCH (ro)-[q:"+ NeoRelEnum.ROLE_POST.code+"]->(p) where ro.mid=$x0 delete q", parameters("x0",mdmRole.getId()))
//            );
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean mergeRoleUserRel(MdmRole mdmRole, List<MdmRoleUser> resultRoleUsers) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            if(CollectionUtil.isNotEmpty(resultRoleUsers)) {
//                Transaction tx = session.beginTransaction();
//                for (MdmRoleUser mdmRoleUser : resultRoleUsers) {
//                    tx.run("MATCH (ro:"+ NeoNodeEnum.ROLE.code+" {mid:$x0})," +
//                            "(u:"+ NeoNodeEnum.USER.code+" {mid:$x1})" +
//                            "MERGE (ro)-[:"+ NeoRelEnum.ROLE_USER.code+" {mid:$x2}]->(u) ", parameters("x0",mdmRole.getId(),"x1",mdmRoleUser.getUserId(),"x2",mdmRoleUser.getId()));
//                }
//                tx.commit();
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean delRoleUserRel(MdmRole mdmRole) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "MATCH (ro)-[q:"+ NeoRelEnum.ROLE_USER.code+"]->(p) where ro.mid=$x0 delete q", parameters("x0",mdmRole.getId()))
//            );
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        }
//        finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean saveResNode(MdmResource mdmResource) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            session.writeTransaction(tx -> tx.run(
//                    "CREATE (u:"+ NeoNodeEnum.RES.code+" {mid: $x0, resourceName: $x1, resourceCode: $x2})", parameters("x0",mdmResource.getId(),"x1",mdmResource.getResourceName(),"x2",mdmResource.getResourceCode())));
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    @Transactional
//    public boolean delNodeByMid(String neoNodeCode, List<String> ids) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            //match(n:User)-[r]-() return r,n
//            Transaction tx = session.beginTransaction();
//            if(CollectionUtil.isNotEmpty(ids)) {
//                for (String id : ids) {
//                    tx.run("MATCH (u:"+neoNodeCode+")-[r]-() where u.mid=$x0 delete r,u", parameters("x0",Long.valueOf(id)));
//                }
//                tx.commit();
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException(e.getMessage());
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public boolean getResByUserId(Long userId) {
//        getSession();
//        try (Session session = driver.session())
//        {
//            Result result = session.run(
//                    "match (na:User{mid:$x0})-[*1..3]-(nb:Res) return nb.mid", parameters("x0",userId));
//            while (result.hasNext()) {
//                Record record = result.next();
//                // Values can be extracted from a record by index or name.
//                System.out.println(record.get("nb.mid").asLong());
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }finally {
//            session.close();
//        }
//    }
}
