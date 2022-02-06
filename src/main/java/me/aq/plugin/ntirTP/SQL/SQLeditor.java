package me.aq.plugin.ntirTP.SQL;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SQLeditor {

    private NTIRTP plugin;
    public void SQLGetter(NTIRTP plugin){
        this.plugin = plugin;

    }

    public void createTable(){

        PreparedStatement ps;
        PreparedStatement ps1;
        PreparedStatement ps2;
        PreparedStatement ps3;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS TPList "
                    + "(RNAME VARCHAR(100), RUUID VARCHAR(100),TNAME VARCHAR(100), TUUID VARCHAR(100), PRIMARY KEY(RNAME))");

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ps1 = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS HomeList "
                    + "(NAME VARCHAR(100), Player VARCHAR(100), UUID VARCHAR(100), Server VARCHAR(100), World VARCHAR(100),x VARCHAR(100), y VARCHAR(100),z VARCHAR(100), date VARCHAR(100), PRIMARY KEY(date))");

            ps1.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ps2 = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS SpawnList "
                    + "(Server VARCHAR(100), World VARCHAR(100),x VARCHAR(100), y VARCHAR(100),z VARCHAR(100), PRIMARY KEY(Server))");

            ps2.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ps3 = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BackList "
                    + "(Player VARCHAR(100), UUID VARCHAR(100), Server VARCHAR(100), World VARCHAR(100),x VARCHAR(100), y VARCHAR(100),z VARCHAR(100), PRIMARY KEY(Player))");

            ps3.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void request(Player requester,Player target) {
        if(!existTP(requester)) {
            try {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO TPList (RNAME,RUUID,TNAME,TUUID) VALUES(?,?,?,?)");
                ps.setString(1, requester.getDisplayName());
                ps.setString(2, requester.getUniqueId().toString());
                ps.setString(3, target.getDisplayName());
                ps.setString(4, target.getUniqueId().toString());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelRequest(Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM TPList WHERE RUUID=?");
            ps.setString(1,p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void setSpawn(String server, Location spawn){
        try {
            delSpawn(server);
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO SpawnList (Server,World,x,y,z) VALUES(?,?,?,?,?)");
            ps.setString(1,server);
            ps.setString(2,spawn.getWorld().getName());
            ps.setString(3, String.valueOf(spawn.getX()));
            ps.setString(4, String.valueOf(spawn.getY()));
            ps.setString(5, String.valueOf(spawn.getZ()));
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setHome(String name, Player p, String server, Location home){

        SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss.SSS");
        Date current = new Date();

        try {

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO HomeList (NAME,Player,UUID,Server,World,x,y,z,date) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,p.getDisplayName());
            ps.setString(3,p.getUniqueId().toString());
            ps.setString(4,p.getServer().getMotd());
            ps.setString(5,home.getWorld().getName());
            ps.setString(6, String.valueOf(home.getX()));
            ps.setString(7, String.valueOf(home.getY()));
            ps.setString(8, String.valueOf(home.getZ()));
            ps.setString(9, date1.format(current));
            ps.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void delHome(String name,Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM HomeList WHERE NAME=? AND UUID=?");
            ps.setString(1,name);
            ps.setString(2,p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException E){
            E.printStackTrace();
        }
    }

    public void delSpawn(String server){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM SpawnList WHERE SERVER=?");
            ps.setString(1,server);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delBack(Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM BackList WHERE UUID=?");
            ps.setString(1,p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void setback(Player p , String server, Location back){

        delBack(p);

        try {

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO BackList (Player,UUID,Server,World,x,y,z) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1,p.getDisplayName());
            ps.setString(2,p.getUniqueId().toString());
            ps.setString(3,server);
            ps.setString(4,back.getWorld().getName());
            ps.setString(5, String.valueOf(back.getX()));
            ps.setString(6, String.valueOf(back.getY()));
            ps.setString(7, String.valueOf(back.getZ()));
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean existTP(Player p){

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT RUUID FROM TPList WHERE RUUID=?");
            ps.setString(1,p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existTPT(Player p){

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT TUUID FROM TPList WHERE TUUID=?");
            ps.setString(1,p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean homeExist(Player p, String name){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT NAME FROM HomeList WHERE UUID=? AND NAME=?");
            ps.setString(1,p.getUniqueId().toString());
            ps.setString(2,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Player getRequester(Player target){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT RNAME FROM TPList WHERE TUUID=?");
            ps.setString(1,target.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            String NAME = null;
            if(rs.next()) {
                NAME = rs.getString("RNAME");
                Player requester = Bukkit.getPlayerExact(NAME);
                return requester;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Player getTarget(Player requester){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT TNAME FROM TPList WHERE RUUID=?");
            ps.setString(1,requester.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            String NAME = null;
            if(rs.next()) {
                NAME = rs.getString("TNAME");
                Player target = Bukkit.getPlayerExact(NAME);
                return target;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    public Location getSpawn(String server){
        try {

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT x FROM SpawnList WHERE Server=?");
            PreparedStatement ps0 = plugin.SQL.getConnection().prepareStatement("SELECT World FROM SpawnList WHERE Server=?");
            PreparedStatement ps1 = plugin.SQL.getConnection().prepareStatement("SELECT y FROM SpawnList WHERE Server=?");
            PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT z FROM SpawnList WHERE Server=?");
            ps0.setString(1,server);
            ps.setString(1,server);
            ps1.setString(1,server);
            ps2.setString(1,server);
            ResultSet rs = ps.executeQuery();
            ResultSet rs0 = ps0.executeQuery();
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            double x = 0;
            double y = 0;
            double z = 0;
            if(rs.next() | rs1.next() | rs2.next()| rs0.next()){
                World world = Bukkit.getWorld(rs0.getString("World"));
                x = Double.parseDouble(rs.getString("x"));
                y = Double.parseDouble(rs1.getString("y"));
                z = Double.parseDouble(rs2.getString("z"));
                Location location = new Location(world,x,y,z);
                return location;
            }
            return null;
        }catch (SQLException e){
         e.printStackTrace();
        }
        return null;
    }

    public Location getBack(String uuid){
        try {

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT x FROM BackList WHERE UUID=?");
            PreparedStatement ps0 = plugin.SQL.getConnection().prepareStatement("SELECT World FROM BackList WHERE UUID=?");
            PreparedStatement ps1 = plugin.SQL.getConnection().prepareStatement("SELECT y FROM BackList WHERE UUID=?");
            PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT z FROM BackList WHERE UUID=?");
            ps0.setString(1,uuid);
            ps.setString(1,uuid);
            ps1.setString(1,uuid);
            ps2.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            ResultSet rs0 = ps0.executeQuery();
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            double x = 0;
            double y = 0;
            double z = 0;
            if(rs.next() | rs1.next() | rs2.next() | rs0.next()){
                World world = Bukkit.getWorld(rs0.getString("World"));
                x = Double.parseDouble(rs.getString("x"));
                y = Double.parseDouble(rs1.getString("y"));
                z = Double.parseDouble(rs2.getString("z"));
                Location location = new Location(world,x,y,z);
                return location;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Location getHome(String name,Player p){

        try {
            PreparedStatement ps1 = plugin.SQL.getConnection().prepareStatement("SELECT World FROM HomeList WHERE NAME=? AND UUID=?");
            PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT x FROM HomeList WHERE NAME=? AND UUID=?");
            PreparedStatement ps3 = plugin.SQL.getConnection().prepareStatement("SELECT y FROM HomeList WHERE NAME=? AND UUID=?");
            PreparedStatement ps4 = plugin.SQL.getConnection().prepareStatement("SELECT z FROM HomeList WHERE NAME=? AND UUID=?");
            ps1.setString(1,name);
            ps2.setString(1,name);
            ps3.setString(1,name);
            ps4.setString(1,name);
            ps1.setString(2,p.getUniqueId().toString());
            ps2.setString(2,p.getUniqueId().toString());
            ps3.setString(2,p.getUniqueId().toString());
            ps4.setString(2,p.getUniqueId().toString());
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs3 = ps3.executeQuery();
            ResultSet rs4 = ps4.executeQuery();

            World world = null;
            double x = 0;
            double y = 0;
            double z = 0;

            if(rs1.next() | rs2.next() | rs3.next() | rs4.next()){

                world = Bukkit.getWorld(rs1.getString("World"));
                x = Double.parseDouble(rs2.getString("x"));
                y = Double.parseDouble(rs3.getString("y"));
                z = Double.parseDouble(rs4.getString("z"));

                Location location = new Location(world,x,y,z);
                return location;

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getHomeSv(String name, Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT Server FROM HomeList WHERE NAME=? AND UUID=?");
            ps.setString(1, name);
            ps.setString(2,p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            String server = null;
            if(rs.next()){
                server = rs.getString("Server");
                return server;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int HomeCount(Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT COUNT(UUID) AS COUNT FROM HomeList WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if(rs.next()){
                count = rs.getInt("COUNT");
                return count;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }



}
