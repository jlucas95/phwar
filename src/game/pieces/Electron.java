package game.pieces;

import game.IPlayer;

public class Electron extends Piece {
    public Electron(IPlayer owner){
        super(-1, owner);
    }

    public Electron(Electron e){
        super(e);
    }

    @Override
    public Electron copy() {
        return new Electron(this);
    }
}
