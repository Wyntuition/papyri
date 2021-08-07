package com.papyri.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Account {
    public String name;
    public String url;
    public String clientId;
    public String schema;
}
