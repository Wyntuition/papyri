package com.papyri.models;

import lombok.Builder;

import java.util.List;

@Builder
public class Person {
    public String name;
    public String email;
    public List<Integer> phones;
    public List<Account> accounts;
}

