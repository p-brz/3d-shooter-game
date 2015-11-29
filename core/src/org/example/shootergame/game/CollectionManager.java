package org.example.shootergame.game;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionManager<T> {
	private final Collection<T> items;
	
	public CollectionManager() {
		items =new LinkedList<T>();
	}
	
	public void add(T ... itens){
		for(T item : itens){
			items.add(item);
		}
	}
	public void remove(T ... itens){
		for(T item : itens){
			items.remove(item);
		}
	}
	public void removeAll(Collection<T> items) {
		this.items.removeAll(items);
	}
	
	public Collection<T> getItems(){
		return items;
	}

	public boolean hasItems(){
		return !items.isEmpty();
	}
}
