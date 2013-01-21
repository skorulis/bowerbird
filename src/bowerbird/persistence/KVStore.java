package bowerbird.persistence;

import bowerbird.common.Commodity;
import bowerbird.common.parser.ParserState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;

public class KVStore {

	private Jedis jedis;
	private Gson gson;
	
	public KVStore() {
		jedis = new Jedis("localhost");
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}
	
	public <T> T load(String id,Class<T> c) {
		String prefix = getPrefix(c);
		String json = jedis.get(prefix+id);
		if(json!=null) {
			return gson.fromJson(json, c);
		}
		return null;
	}
	
	public void save(Persistable p) {
		String prefix = getPrefix(p.getClass());
		jedis.set(prefix + p.id(), gson.toJson(p));
	}
	
	public static String getPrefix(Class<?> c) {
		if(c==ParserState.class) {
			return "PS:";
		} else if(c==Commodity.class) {
			return "COM";
		}
		return null;
	}
	
}
