package me.playbosswar.devroomquests.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import me.playbosswar.devroomquests.Quests;

public class Database {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public Database() {
        String link = Quests.getInstance().getConfig().getString("mongo");
        ConnectionString connectionString = new ConnectionString(link);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("devroom_quests");
    }
}
