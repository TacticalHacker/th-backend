package com.tacticalhacker.th_scribes_backend.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "git")
public class GitData {
    private String branch;
    private Build build;
    private Closest closest;
    private Commit commit;
    private Boolean dirty;
    private Remote remote;
    private String tags;

    @Data
    public static class Build {
        private String host;
        private String time;
        private User user;
        private String version;

        @Data
        public static class User {
            private String email;
            private String name;
        }
    }

    @Data
    public static class Closest {
        private Tag tag;

        @Data
        public static class Tag {
            private Commit commit;
            private String name;

            @Data
            public static class Commit {
                private Integer count;
            }
        }
    }

    @Data
    public static class Commit {
        private Id id;
        private Message message;
        private String time;
        private User user;

        @Data
        public static class Id {
            private String abbrev;
            private String describe;
            private String describeShort;
        }

        @Data
        public static class Message {
            private String full;
        }

        @Data
        public static class User {
            private String email;
            private String name;
        }
    }

    @Data
    public static class Remote {
        private Origin origin;

        @Data
        public static class Origin {
            private String url;
        }
    }
}
