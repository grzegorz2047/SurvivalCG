package pl.grzegorz2047.api.chests;

import pl.grzegorz2047.api.manager.CoreManager;

/**
 * Created by Grzegorz2047. 01.09.2015.
 */
public class ChestManager {
    private CoreManager coreManager;

    public ChestManager(CoreManager coreManager) {
        this.coreManager = coreManager;
    }
/*
    private int amountOfItemsInChest = 6;
    private boolean editMode = false;
    private Plugin plugin;
    YmlFileHandler chestFileHandler;
    YmlFileHandler chestItemsFileHandler;
    private static Random r = new Random();
    private List<Location> chests = new ArrayList<Location>();
    private List<Location> doubleChests = new ArrayList<Location>();

    private List<RandomItem> randomItems = new ArrayList<RandomItem>();

    public ChestManager(Plugin plugin) {
        this.plugin = plugin;
        chestFileHandler = new YmlFileHandler(sg, "/home/grzegorzServer/Mapy/Mapy_SG/"+File.separator+sg.getWorldName(), "ChestSpawns");
        chestFileHandler.load();

        chestItemsFileHandler = new YmlFileHandler(sg,"/home/grzegorzServer/Config"+ File.separator, "ChestItems");
        chestItemsFileHandler.load();
        this.loadChestsConfig();
        this.loadRandomItems();
        this.fillChests();
    }

    public void addChest(Location loc) {
        this.chests.add(loc);
    }

    public void addDoubleChest(Location loc) {
        this.doubleChests.add(loc);
    }


    public void saveChestsLocToFile() {
        YmlFileHandler file = this.chestFileHandler;
        file.getConfig().set("numOfChests", this.chests.size());

        StringBuilder sb;
        int index = 0;
        for (Location loc : this.chests) {
            sb = new StringBuilder();
            sb.append(loc.getBlockX()).append(':').append(loc.getBlockY()).append(':').append(loc.getBlockZ()).append(':').append(loc.getWorld().getName());
            String cords = sb.toString();
            file.getConfig().set("chests." + index, cords);
            index++;
        }
        file.save();
    }

    public void saveDoubleChestsLocToFile() {
        YmlFileHandler file = this.chestFileHandler;
        file.getConfig().set("numOfDoubleChests", this.doubleChests.size());

        StringBuilder sb;
        int index = 0;
        for (Location loc : this.doubleChests) {
            sb = new StringBuilder();
            sb.append(loc.getBlockX()).append(':').append(loc.getBlockY()).append(':').append(loc.getBlockZ()).append(':').append(loc.getWorld().getName());
            String cords = sb.toString();
            file.getConfig().set("doublechests." + index, cords);
            index++;
        }
        file.save();
    }

    public void fillChests() {
        List<Location> locToRemove = new ArrayList<Location>();
        for (Location loc : this.chests) {
            if(sg.isDebugMode()){
                if(loc == null){
                    locToRemove.add(loc);
                    System.out.println("Niepoprawna loc skrzynki!");
                }
            }

            Block b = loc.getBlock();
            int amount = r.nextInt(amountOfItemsInChest) + 2;
            if (b.getState() instanceof Chest) {
                Chest chest = (Chest) b.getState();
                for (int i = 1; i < amount; i++) {
                    chest.
                            getInventory().
                            addItem(this.randomItems.
                                    get(r.nextInt(this.randomItems.size() - 1))
                                    .getItemStack());
                }

            }
            if (b.getState() instanceof DoubleChest) {
                DoubleChest chest = (DoubleChest) b.getState();
                for (int i = 1; i < amount; i++) {
                    chest.getInventory().addItem(this.randomItems.get(r.nextInt(this.randomItems.size() - 1)).getItemStack());
                }

            }
        }
        if(sg.isDebugMode()){
            for(Location loc : locToRemove){
                this.chests.remove(loc);
            }
        }

    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public List<Location> getChests() {
        return chests;
    }

    public List<Location> getDoubleChests() {
        return doubleChests;
    }

    public List<RandomItem> getRandomItems() {
        return randomItems;
    }

    public void loadChestsConfig() {
        int numOfChests = this.chestFileHandler.getConfig().getInt("numOfChests");
        MsgManager.debug("Liczba skrzynek " + numOfChests + " do wczytania");
        if (numOfChests != 0) {
            for (int i = 0; i < numOfChests; i++) {
                String spawnPointString = this.chestFileHandler.getConfig().getString("chests." + i);
                String[] spArray = spawnPointString.split(":");
                double x = Double.parseDouble(spArray[0]);
                double y = Double.parseDouble(spArray[1]);
                double z = Double.parseDouble(spArray[2]);
                String worldName = spArray[3];

                Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
                this.chests.add(loc);
            }
        } else {
            MsgManager.debug("Brak skrzynek do wczytania!");
        }

        int numOfDoubleChests = this.chestFileHandler.getConfig().getInt("numOfDoubleChests");
        MsgManager.debug("Liczba skrzynek podwojnych " + numOfDoubleChests + " do wczytania");
        if (numOfDoubleChests != 0) {
            for (int i = 0; i < numOfDoubleChests; i++) {
                String spawnPointString = this.chestFileHandler.getConfig().getString("doublechests." + i);
                String[] spArray = spawnPointString.split(":");
                double x = Double.parseDouble(spArray[0]);
                double y = Double.parseDouble(spArray[1]);
                double z = Double.parseDouble(spArray[2]);
                String worldName = spArray[3];

                Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
                this.doubleChests.add(loc);
            }
        } else {
            MsgManager.debug("Brak podwojnych skrzynek do wczytania!");
        }

    }

    public void loadRandomItems() {
        List<String> items = this.chestItemsFileHandler.getConfig().getStringList("items");
        for (String data : items) {
            System.out.println("Item " + data);
            this.randomItems.add(new RandomItem(data));
        }
    }
*/
}
