package com.olyno.gamet.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.olyno.gamet.Gamet;

public class PackageLoader<T> {
    
    private String packagePath;
    private String what;
    private CompletableFuture<LinkedList<T>> instancesList;

    public PackageLoader(String packagePath, String what) {
        this.packagePath = packagePath;
        this.what = what;
        instancesList = CompletableFuture.supplyAsync(() -> {
            LinkedList<T> classList = new LinkedList<>();
            try {
                List<Class<T>> classes = new PackageFilter<T>(this.packagePath).getClasses();
                for (Class<T> event : classes) {
                    T classInstance = event.getDeclaredConstructor().newInstance();
                    classList.add(classInstance);
                }
            } catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                Gamet.error("Something was wrong when trying to " + this.what + ". Please create an issue with this error:");
                ex.printStackTrace();
            }
            return classList;
        });
    }

    public CompletableFuture<LinkedList<T>> getList() {
        return instancesList;
    }

}