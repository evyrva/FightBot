package com.company.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PartOfBody {
    HEAD {
        @Override
        public String toString() {
            return "Голова";
        }
    },
    HANDS {
        @Override
        public String toString() {
            return "Руки";
        }
    },
    STOMACH {
        @Override
        public String toString() {
            return "Живот";
        }
    },
    LEGS {
        @Override
        public String toString() {
            return "Ноги";
        }
    };

    public static String[] getBodyTextList() {
        List<String> out = new ArrayList<>();
        Arrays.stream(PartOfBody.values()).forEach(r -> out.add(r.toString()));
        return out.toArray(new String[PartOfBody.values().length]);
    }

    public static String[] getBodyDataList() {
        List<String> out = new ArrayList<>();
        Arrays.stream(PartOfBody.values()).forEach(r -> out.add(r.name()));
        return out.toArray(new String[PartOfBody.values().length]);
    }


}
