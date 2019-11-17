package io.maxilog.oidc;

import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/api/users")
public class UsersResource {

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public User me() {
        return new User(identity);
    }

    @GET
    @Path("/")
    @RolesAllowed("ROLE_ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(){
        return IntStream.rangeClosed(0,10)
                .mapToObj(i -> new User("user"+i))
                .collect(Collectors.toList());
    }

    public class User {

        private final String userName;

        public User(String userName) {
            this.userName = userName;
        }

        User(SecurityIdentity identity) {
            this.userName = identity.getPrincipal().getName();
        }

        public String getUserName() {
            return userName;
        }
    }
}
