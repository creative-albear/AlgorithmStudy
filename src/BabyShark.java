import java.util.*;

public class BabyShark {
    private static final int SHARK_MARKING = 9;
    public static void main(String[] args){
        int[][] cubeArray = makeCube(new Scanner(System.in));
        new BabyShark().doProcess(cubeArray);
    }

    private void doProcess(int[][] cubeArray){
        Cube cube = new Cube(cubeArray);

        // 상어 기본 위치 세팅
        Shark startShark = null;
        for(int x=0; x<cube.getLength(); x++){
            for(int y=0; y<cube.getLength(); y++) {
                if (cube.getFish(x, y) == SHARK_MARKING) {
                   startShark = new Shark(x, y);
                   break;
                }
            }
        }

        if(startShark == null) throw new IllegalArgumentException();

        int[][] moveDirction = { {0, -1},{-1, 0},{0, 1},{1, 0} }; // 북, 서, 남, 동

        while(true) {
            Queue<Shark> queue = new LinkedList<>();
            List<Fish> fishes = new ArrayList<>();
            queue.offer(startShark);
            cube.setVisited(startShark.x, startShark.y);

            while (queue.isEmpty()) {
                Shark shark = queue.poll();
                int cx = shark.x;
                int cy = shark.y;
                for (int[] point : moveDirction) {
                    int mx = cx + point[0];
                    int my = cy + point[1];

                    // 지도가 넘어가면 pass
                    if (mx < 0 || mx >= cube.getLength() || my < 0 || my >= cube.getLength()) {
                        continue;
                    }

                    // 못먹으면 pass
                    if (cube.getFish(mx, my) >= shark.size || cube.isVisited(mx, my)) {
                        continue;
                    }

                    if (cube.getFish(mx, my) < shark.size) {
                        fishes.add(new Fish(mx, my));
                        queue.offer(new Shark(mx, my, shark.size, shark.eatCount + 1, shark.time + 1));
                    }
                }
            }
        }
    }


    private static int[][] makeCube(Scanner scanner){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        System.out.println("size : "+ N);
        int[][] cube = new int[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                cube[i][j] = sc.nextInt();
            }
        }

        // Input Value Check
//        for(int i=0; i<N; i++){
//            for(int j=0; j<N; j++){
//                 System.out.print(cube[i][j] + " ");
//            }
//            System.out.println();
//        }

        return cube;
    }

    private class Cube{
        private int[][] cube;
        private boolean[][] visited;
        public static final int DIRECTION_NORTH = 1;
        public static final int DIRECTION_EAST  = 2;
        public static final int DIRECTION_WEST  = 4;
        public static final int DIRECTION_SOUTH = 8;

        public int getLength(){
            return cube.length;
        }

        public Cube(int[][] cube){
            this.cube = cube;
            visited = new boolean[cube.length][cube.length];
        }

        public int getFish(int x, int y){
            return this.cube[x][y];
        }

        public boolean isVisited(int x, int y){
            return visited[x][y];
        }

        public void setVisited(int x, int y){
            visited[x][y] = true;
        }
    }


    private class Shark{
        public int x;
        public int y;
        public int size = 2;
        public int eatCount = 0;
        public int time = 0;

        Shark(int x, int y){
            this.x = x;
            this.y = y;
        }
        Shark(int x, int y, int size, int eatCount, int time){
            this.x = x;
            this.y = y;
        }
    }

    private class Fish{
        public int x;
        public int y;

        Fish(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}