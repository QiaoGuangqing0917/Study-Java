package com.qgq;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import junit.framework.TestCase;
import org.apache.hadoop.util.bloom.CountingBloomFilter;
import org.apache.hadoop.util.bloom.Key;
import org.apache.hadoop.util.hash.Hash;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ApplicationTest extends TestCase {

	@Test
	public void testBloom() {
		int total = 10000000;

		BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(),total,0.02D);

		for(long i=0;i<total;i++){
			bloomFilter.put(i);
		}

		int count = 0;
		for(long i=0;i<total;i++){
			if(bloomFilter.mightContain(i)){
				count ++;
			}
		}
		System.out.println("匹配本身的次数为:"+count);

		count = 0;
		for(long i=total;i<total+10000;i++){
			if(bloomFilter.mightContain(i)){
				count ++;
			}
		}
		System.out.println("匹配不存在10000次的结果为："+count+" 误报率为："+((float)count/10000)+"%");
	}

	@Test
	public void testCountingBloom(){

		CountingBloomFilter countingBloomFilter = new CountingBloomFilter(3, 2, Hash.MURMUR_HASH);
		List<Key> keyList = new ArrayList<>();
		keyList.add(new Key("1".getBytes()));
		keyList.add(new Key("2".getBytes()));
		keyList.add(new Key("3".getBytes()));
		keyList.add(new Key("4".getBytes()));
		keyList.add(new Key("5".getBytes()));
		countingBloomFilter.add(keyList);

		System.out.println("5在过滤器中吗？"+countingBloomFilter.membershipTest(new Key("10".getBytes())));

		countingBloomFilter.delete(new Key("3".getBytes()));

		System.out.println("3在过滤器中吗？"+countingBloomFilter.membershipTest(new Key("12".getBytes())));

	}
}