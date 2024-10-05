package edu.project.myrest;

import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    MeterRegistry meterRegistry;
    Counter counter;
    Gauge gauge;
    Timer timer;
    DistributionSummary summary;

    List<Post> list = new ArrayList<>();
    public PostService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        counter=Counter
                .builder("getpost.count")
                .tag("author","alex")
                .register(meterRegistry);
        gauge=Gauge
                .builder("testValue",list::size)
                .tag("database","size")
                .register(meterRegistry);
        timer=Timer
                .builder("search.time")
                .register(meterRegistry);

    }

    public void addPost(Post post) {
        list.add(post);
    }

    public Post getPost(int id) {
        Timer.Sample timer=Timer.start(meterRegistry);

        counter.increment();
        gauge.measure();
        try {
            Thread.sleep(100);
        }catch (Exception ex){
            //no op
        }
        for(Post p:list){
            if (p.id()==id) {
                timer.stop(meterRegistry.timer("search.time"));
                return p;
            }
        }
        timer.stop(meterRegistry.timer("search.time"));
        return null;
    }
}
