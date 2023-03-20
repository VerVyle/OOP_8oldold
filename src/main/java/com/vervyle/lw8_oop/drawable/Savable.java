package com.vervyle.lw8_oop.drawable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public interface Savable extends Serializable {
    default void save(ObjectOutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        try {
            outputStream.writeObject(this);
        } catch (IOException e) {
            System.out.println("Не удалось сохранить объект\n");
            e.printStackTrace();
        }
    }


    default Object load(ObjectInputStream objectInputStream) {
        Objects.requireNonNull(objectInputStream);
        Object obj = null;
        try {
            obj = objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось найти класс: " + this.getClass().getName() + "\n");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Не удалось прочитать объект\n");
            e.printStackTrace();
        }
        return obj;
    }
}
