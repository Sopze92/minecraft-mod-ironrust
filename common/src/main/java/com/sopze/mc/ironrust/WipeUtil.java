package com.sopze.mc.ironrust;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.world.level.ChunkPos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WipeUtil {

  public static List<String> ZONE_NAMES= new ArrayList<>();

  public static List<String> TAGS_GETLISTREADVIEW= new ArrayList<>();
  public static List<String> TAGS_GET= new ArrayList<>();
  public static List<String> TAGS_GETLIST= new ArrayList<>();
  public static List<String> TAGS_GETCOMPOUND= new ArrayList<>();

  public static JsonObject loadInternalJson(String path) throws IOException {
    try (InputStream stream = Main.class.getClassLoader().getResourceAsStream(path)) {
      if (stream == null) throw new FileNotFoundException("loadInternalJson() -> File not found: " + path);
      return JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
    }
  }

  public static class ThreadTrackerUtil {

    private static final ThreadTrackerUtil _empty= new ThreadTrackerUtil("-", "-", null, null);
    private static final ConcurrentMap<Long, ThreadTrackerUtil> _THREAD_TRACKER = new ConcurrentHashMap<>(256);

    private final Thread _thread; private String _task; private String _zone; private Path _path; private ChunkPos _chunkPos; private String _chunkPosString;

    public long id() { return _thread.threadId(); }
    public String info(){ return String.format("[%d:%s:%s, %s]", _thread.threadId(), _task, _zone, file()); }
    public String task() { return _task; }
    public String zone() { return _zone; }
    public boolean storage() { return _path != null; }
    public String file() { return _path != null ? _path.getFileName().toString() : "-memory-"; }
    public String path() { return _path != null ? _path.toString() : "-memory-"; }
    public boolean chunk() { return _chunkPos != null; }
    public ChunkPos chunkPos() { return _chunkPos; }
    public String chunkString() { return _chunkPosString; }
    private void _setChunkPos(ChunkPos pos){
      _chunkPos= pos;
      if(pos != null) {
        _chunkPosString= String.format("%d,%d", _chunkPos.x, _chunkPos.z);
        _path= new File(String.format("r.%d.%d.mca", _chunkPos.getRegionX(), _chunkPos.getRegionZ())).toPath();
      }
    }

    private ThreadTrackerUtil(){ _thread = Thread.currentThread(); _chunkPosString= ""; }
    private ThreadTrackerUtil(String task, String zone, Path path, ChunkPos pos){ this(); _task= task; _zone = zone; _path = path; _chunkPos= pos; }
    public static ThreadTrackerUtil withTask(String task){ return new ThreadTrackerUtil(task, "-", null, null); }
    public static ThreadTrackerUtil withZone(String zone){ return new ThreadTrackerUtil("-", zone, null, null); }
    public static ThreadTrackerUtil withPath(Path value){ return new ThreadTrackerUtil("-", "-", value, null); }
    public static ThreadTrackerUtil withChunk(ChunkPos chunkPos){ return new ThreadTrackerUtil("-", "-", null, chunkPos); }

    public static ThreadTrackerUtil get(long id) { return _THREAD_TRACKER.get(id) instanceof ThreadTrackerUtil ttu ? ttu : _empty; }
    public static ThreadTrackerUtil get() { return _THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil ttu ? ttu : _empty; }

    public static void checkThreadAliveState(){
      for(Long k : _THREAD_TRACKER.keySet()){
        if(_THREAD_TRACKER.get(k) instanceof ThreadTrackerUtil tt && !tt._thread.isAlive()) {
          _THREAD_TRACKER.remove(k);
          System.out.printf("Removed thread %s from tracking (no longer alive)%n", k);
        }
      }
    }

    // task

    public static boolean isThreadInTask(String task) { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) && m._task.equals(task); }
    public static String getThreadTask() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) ? m._zone : "-"; }

    public static void updateThreadTask(String task) {
      checkThreadAliveState();
      long tid= Thread.currentThread().threadId();
      if(_THREAD_TRACKER.get(tid) instanceof ThreadTrackerUtil m) m._task = task;
      else _THREAD_TRACKER.put(tid, ThreadTrackerUtil.withTask(task));
    }

    // zone

    public static boolean isThreadInZone(String zone) { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) && m._zone.equals(zone); }
    public static String getThreadZone() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) ? m._zone : "-"; }

    public static void updateThreadZone(String zone) {
      checkThreadAliveState();
      long tid= Thread.currentThread().threadId();
      if(_THREAD_TRACKER.get(tid) instanceof ThreadTrackerUtil m) m._zone = zone;
      else _THREAD_TRACKER.put(tid, ThreadTrackerUtil.withZone(zone));
    }

    // path

    public static boolean hasThreadFilePath() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) && m._path != null; }
    public static String getThreadFilePath() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) ? m.path() : null; }
    public static String getThreadFileName() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) ? m.file() : null; }

    public static void updateLevelStorage(Path value) {
      checkThreadAliveState();
      long tid= Thread.currentThread().threadId();
      if(_THREAD_TRACKER.get(tid) instanceof ThreadTrackerUtil m) m._path = value;
      else _THREAD_TRACKER.put(tid, ThreadTrackerUtil.withPath(value));
    }

    // chunk

    public static boolean isThreadInChunk() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) && m._chunkPos != null; }
    public static ChunkPos getThreadChunkPos() { return (_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) ? m._chunkPos : null; }

    public static String getThreadChunkPosString() {
      if(_THREAD_TRACKER.get(Thread.currentThread().threadId()) instanceof ThreadTrackerUtil m) return m.chunkString();
      return "";
    }

    public static void updateThreadChunk(ChunkPos pos) {
      checkThreadAliveState();
      long tid= Thread.currentThread().threadId();
      if(_THREAD_TRACKER.get(tid) instanceof ThreadTrackerUtil m) m._setChunkPos(pos);
      else _THREAD_TRACKER.put(tid, ThreadTrackerUtil.withChunk(pos));
    }
  }
}
