/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 12:48
 */

package sphynx.generator;

import sphynx.model.Component;
import sphynx.model.ComponentDatum;
import sphynx.model.Composite;

public class CompositeBuilder {
	private CompositeBuilder() {  }

	public static CompositeBuilder create() {
		return new CompositeBuilder();
	}

	public ComponentBuilder addComponent(String identifier) {
		return new ComponentBuilder(this);
	}

	public static class ComponentBuilder {
		private CompositeBuilder parentCompositeBuilder;
		private ComponentBuilder parentComponentBuilder;
		private Component currentComponent;

		private ComponentBuilder(CompositeBuilder parentCompositeBuilder) {
			this.currentComponent = new Component();
			this.parentCompositeBuilder = parentCompositeBuilder;
		}

		private ComponentBuilder(ComponentBuilder parentComponentBuilder) {
			this.currentComponent = new Component();
			this.parentComponentBuilder = parentComponentBuilder;
		}

		public ComponentBuilder setName(String name) {
			ComponentDatum nameDatum = currentComponent.componentData.get("name");
			nameDatum.data = new String[] { name };
			return this;
		}

		public ComponentBuilder setModifiers(String... modifiers) {
			ComponentDatum modifierDatum = currentComponent.componentData.get("modifier");
			modifierDatum.data = modifiers;
			return this;
		}

		public CompositeBuilder finalizeComponent() {
			if(this.hasParentComponentBuilder())
				return //FUCK *!$§
			return parentCompositeBuilder;
		}

		public ComponentBuilder addComponent(String name) {
			return new ComponentBuilder(this);
		}
	}
}
