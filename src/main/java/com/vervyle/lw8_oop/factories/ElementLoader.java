package com.vervyle.lw8_oop.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vervyle.lw8_oop.drawable.*;
import com.vervyle.lw8_oop.drawable.leafs.*;

import java.io.*;
import java.util.Objects;

public class ElementLoader {
    public static GraphicElement loadElement(ObjectMapper objectMapper, String type, String value) {
        ElementType elementType = null;
        try {
            elementType = objectMapper.readValue(type, ElementType.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GraphicElement result = null;
        try {
            switch (Objects.requireNonNull(elementType)) {
                case POLYGON -> result = objectMapper.readValue(value, PPolygon.class);
                case PENTAGON -> result = objectMapper.readValue(value, PPentagon.class);
                case TRIANGLE -> result = objectMapper.readValue(value, TTriangle.class);
                case GROUP -> result = objectMapper.readValue(value, GGroup.class);
                case CIRCLE -> result = objectMapper.readValue(value, CCircle.class);
                case SQUARE -> result = objectMapper.readValue(value, SSquare.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
