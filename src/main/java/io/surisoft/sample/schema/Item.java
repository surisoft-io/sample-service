package io.surisoft.sample.schema;

import lombok.Data;

@Data
public class Item {
    private String id;
    private String name;
    private String host;
    private int port;
}