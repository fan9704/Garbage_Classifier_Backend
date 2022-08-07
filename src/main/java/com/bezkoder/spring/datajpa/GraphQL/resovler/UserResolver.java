package com.bezkoder.spring.datajpa.GraphQL.resovler;

import com.bezkoder.spring.datajpa.model.User;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
@Component
public class UserResolver implements GraphQLResolver<User> {
}