package io.surisoft.sample.controller;

import io.surisoft.sample.schema.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.cache2k.Cache;
import org.cache2k.CacheEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@Tag(name="Item Service", description = "Manage Your Items")
@Slf4j
public class ItemController {

    @Autowired
    private Cache<String, Item> cache;


    @PostMapping(path = "/sample/item", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an Item", description = "Only authenticated applications!", security = {
            @SecurityRequirement(name = "bearerToken")})
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        if(item.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        item.setId(UUID.randomUUID().toString());
        cache.put(item.getId(), item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping(path = "/sample/item", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all Items", description = "Open")
    public ResponseEntity<Set<CacheEntry<String, Item>>> getItems() {
        return new ResponseEntity<>(cache.entries(), HttpStatus.OK);
    }

    @GetMapping(path = "/sample/item/{item}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Item by ID", description = "Open")
    public ResponseEntity<Item> getCountryByIso(@PathVariable String item) {
        if(cache.get(item) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cache.get(item), HttpStatus.OK);
    }
}