package com.github.fashionbrot.test;


import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
public class Configuration {

    private Class[] validatedGroups;
    private boolean failFast;
    private String language;
    private String springProfilesActive;

}
