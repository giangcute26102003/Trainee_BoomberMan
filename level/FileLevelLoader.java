package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được từ
     * ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) throws LoadLevelException {
        String path = "levels/Level" + level + ".txt";
        try {
            URL absPath = FileLevelLoader.class.getResource("/" + path);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(absPath.openStream()))) {
                String data = br.readLine();
                StringTokenizer tokens = new StringTokenizer(data);

                _level = Integer.parseInt(tokens.nextToken());
                _height = Integer.parseInt(tokens.nextToken());
                _width = Integer.parseInt(tokens.nextToken());

                _map = new char[_height][_width];
                for (int i = 0; i < _height; i++) {
                    String _lineMap = br.readLine();
                    for (int j = 0; j < _width; j++) {
                        _map[i][j] = _lineMap.charAt(j);

                    }
                }
            }
        } catch (IOException e) {
            throw new LoadLevelException("Lỗi đường truyền " + path, e);
        }
    }

    @Override
    public void createEntities() {
        // TODO: tạo các Entity của màn chơi
        // TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

        // TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
        // TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
        // thêm Wall #
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int pos = x + y * getWidth();

                switch (_map[y][x]) {
                    case '#': {
                        _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                        break;
                    }
                    case 'p': {
                        _board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        Screen.setOffset(0, 0);
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    }
                    case '*': {
                        _board.addEntity(pos,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    }
                    case '1': {
                        _board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    }
                    case '2': {
                        _board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    }
                    case 'x': {
                        _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
                                new Portal(x, y, Sprite.portal),
                                new Brick(x, y, Sprite.brick)
                        ));
                        break;
                    }
                    case 'f': {
                        _board.addEntity(pos,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new SpeedItem(x, y, Sprite.powerup_flames),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    }
                    default:
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                }
            }
        }

//        // thêm Bomber
////        int xBomber = 1, yBomber = 1;
////        _board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
////        Screen.setOffset(0, 0);
////        _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
//
//        // thêm Enemy
//        int xE = 2, yE = 1;
//        _board.addCharacter(new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
//        _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
//
//        // thêm Brick
//        int xB = 3, yB = 1;
//        _board.addEntity(xB + yB * _width,
//                new LayeredEntity(xB, yB,
//                        new Grass(xB, yB, Sprite.grass),
//                        new Brick(xB, yB, Sprite.brick)
//                )
//        );
//
//        // thêm Item kèm Brick che phủ ở trên
//        int xI = 1, yI = 2;
//        _board.addEntity(xI + yI * _width,
//                new LayeredEntity(xI, yI,
//                        new Grass(xI, yI, Sprite.grass),
//                        new SpeedItem(xI, yI, Sprite.powerup_flames),
//                        new Brick(xI, yI, Sprite.brick)
//                )
//        );
    }

}
