package me.playbosswar.devroomquests.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import me.playbosswar.devroomquests.Quests;
import me.playbosswar.devroomquests.quests.PlayerStat;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Main database class. Since we are only going to have 1 collection, we imply that every query is for the players
 * collection
 */
public class Database {
    private final MongoClient mongoClient;
    private final MongoCollection<PlayerStat> playerStatCollection;

    public Database() {
        DatabaseConfiguration dbConfig = new DatabaseConfiguration(Quests.getInstance().getConfig());

        if (!dbConfig.isConfigured()) {
            throw new NullPointerException("MongoDB is not correctly configured. Please check your config.yml file");
        }

        ConnectionString connectionString = new ConnectionString(dbConfig.getLink());
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(dbConfig.getDatabase());
        this.playerStatCollection =
                database.getCollection(dbConfig.getCollection(), PlayerStat.class).withCodecRegistry(codecRegistry);
    }

    public void close() { mongoClient.close(); }

    public List<PlayerStat> findAll(Document query) {
        List<PlayerStat> documents = new ArrayList<>();

        playerStatCollection.find(query).forEach(documents::add);

        return documents;
    }

    public void replace(PlayerStat playerStat) {
        Document filter = new Document("_id", playerStat.getId());
        Document update = new Document("$set", new Document("amount", playerStat.getAmount()));
        playerStatCollection.updateOne(filter, update);
    }

    public void insertMany(List<PlayerStat> dataElements) { playerStatCollection.insertMany(dataElements); }
}
