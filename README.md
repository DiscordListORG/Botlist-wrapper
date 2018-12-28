# BotlistWrapper
### An universal Java Discord botlist wrapper made for use in Discordlist projects
[![Maintainability](https://api.codeclimate.com/v1/badges/cb613bca77e0705b0326/maintainability)](https://codeclimate.com/github/DiscordListORG/Botlist-wrapper/maintainability)
[![Pipeline](https://gitlab.discordlist.org/discordlist-org/botlist-wrapper/badges/master/pipeline.svg)](https://gitlab.discordlist.org/discordlist-org/botlist-wrapper/pipelines)

## Download it
Find the latest version [here](https://gitlab.discordlist.org/discordlist-org/botlist-wrapper/-/packages)
Maven: 
```XML
    <repositories>
        <repository>
            <id>gitlab-maven</id>
            <url>https://gitlab.discordlist.org/api/v4/projects/26/packages/maven</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>org.discordlist</groupId>
            <artifactId>botlist-wrapped</artifactId>
            <version>VERSION FROM LINK ABOVE</version>
        </dependency>
    </dependencies>
            
```

Gradle:
```Groovy
dependencies {
    compile 'org.discordlist:botlist-wrapper:VERSION'
}

repositories {
    maven { url 'https://gitlab.discordlist.org/api/v4/projects/26/packages/maven' }
}
```

Example:
```Java
     HashMap<String, String> tokens = new HashMap<>();
        tokens.put(DiscordBotsGG.class.getCanonicalName(), "dbggtoken");

        BotlistWrapper wrapper = new BotlistWrapperBuilder(
                new JDAProvider(jda),
                botlist -> tokens.get(botlist.getCanonicalName())
        )
                .registerBotlist(new DiscordBotsGG())
                .build();
                
```                

