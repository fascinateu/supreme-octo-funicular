package Chat3;

import java.util.*;
public class HashMaps {
	
		Map<String, Stream> hashmap = null;
		public HashMaps(){	//��ʼ��
			hashmap = new HashMap<String, Stream>();
		}
		public void put(String key, Stream value){	//����³�Ա
			hashmap.put(key, value);
		}
		public Stream get(String key){	//ͨ��key��ȡvalue
			return hashmap.get(key);
		}
		public boolean containsKey(String key){	//�жϿ����Ƿ����
			return hashmap.containsKey(key);
		}

}
