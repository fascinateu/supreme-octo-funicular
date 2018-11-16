package Chat3;

import java.util.*;
public class HashMaps {
	
		Map<String, Stream> hashmap = null;
		public HashMaps(){	//初始化
			hashmap = new HashMap<String, Stream>();
		}
		public void put(String key, Stream value){	//添加新成员
			hashmap.put(key, value);
		}
		public Stream get(String key){	//通过key获取value
			return hashmap.get(key);
		}
		public boolean containsKey(String key){	//判断可以是否存在
			return hashmap.containsKey(key);
		}

}
