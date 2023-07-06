package com.stedi.randomimagegenerator;

import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountTest {
    @Test
    public void test() {
        Rig.Builder builder = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(100).setFixedHeight(100);

        assertEquals(1, builder.build().getCount());

        builder.setCount(13);
        assertEquals(13, builder.build().getCount());

        builder.setWidthRange(1, 10, 1);
        assertEquals(10, builder.build().getCount());

        builder.setWidthRange(10, 10, 1);
        assertEquals(2, builder.build().getCount());

        builder.setWidthRange(1, 10, 5);
        assertEquals(3, builder.build().getCount());

        builder.setFixedWidth(1);

        builder.setHeightRange(10, 1, 1);
        assertEquals(10, builder.build().getCount());

        builder.setHeightRange(10, 10, 100);
        assertEquals(2, builder.build().getCount());

        builder.setHeightRange(10, 1, 5);
        assertEquals(3, builder.build().getCount());

        builder.setWidthRange(1, 10, 5);
        builder.setHeightRange(10, 1, 5);
        assertEquals(9, builder.build().getCount());

        builder.setWidthRange(100, 1000, 100);
        builder.setHeightRange(500, 100, 100);
        assertEquals(50, builder.build().getCount());
    }
}
