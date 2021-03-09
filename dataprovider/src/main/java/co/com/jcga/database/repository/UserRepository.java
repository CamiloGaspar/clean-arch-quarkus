package co.com.jcga.database.repository;

import javax.inject.Singleton;

import co.com.jcga.database.model.UserModel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class UserRepository implements PanacheRepositoryBase<UserModel, String> {


}
