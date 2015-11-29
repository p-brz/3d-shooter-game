package org.example.shootergame.game;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.utils.Disposable;

public class DisposableManager implements Disposable{
	private final Collection<Disposable> disposables;
	
	public DisposableManager() {
		disposables = new LinkedList<Disposable>();
	}

	public void add(Disposable disposable){
		this.disposables.add(disposable);
	}
	public void add(Disposable ... disposables){
		for(Disposable d : disposables){
			add(d);
		}
	}
	public void remove(Disposable disposable){
		this.disposables.remove(disposable);
	}
	
	@Override
	public void dispose() {
		for(Disposable d : disposables){
			d.dispose();
		}
	}
}
