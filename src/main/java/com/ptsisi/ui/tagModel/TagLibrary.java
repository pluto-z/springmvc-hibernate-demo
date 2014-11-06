package com.ptsisi.ui.tagModel;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ptsisi.ui.component.Component;
import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.tags.Agent;
import com.ptsisi.ui.component.tags.Button;
import com.ptsisi.ui.component.tags.Checkbox;
import com.ptsisi.ui.component.tags.Checkboxes;
import com.ptsisi.ui.component.tags.Date;
import com.ptsisi.ui.component.tags.Div;
import com.ptsisi.ui.component.tags.Email;
import com.ptsisi.ui.component.tags.Foot;
import com.ptsisi.ui.component.tags.Form;
import com.ptsisi.ui.component.tags.Grid;
import com.ptsisi.ui.component.tags.Head;
import com.ptsisi.ui.component.tags.Password;
import com.ptsisi.ui.component.tags.Radio;
import com.ptsisi.ui.component.tags.Radios;
import com.ptsisi.ui.component.tags.Select;
import com.ptsisi.ui.component.tags.Select2;
import com.ptsisi.ui.component.tags.Submit;
import com.ptsisi.ui.component.tags.Textarea;
import com.ptsisi.ui.component.tags.Textfield;
import com.ptsisi.ui.component.tags.Textfields;
import com.ptsisi.ui.component.tags.Validity;

public class TagLibrary {

  protected ComponentContext context;

  protected final Map<Class<?>, TagModel> models = Maps.newHashMap();

  public TagLibrary(ComponentContext context) {
    this.context = context;
  }

  protected TagModel get(final Class<? extends Component> clazz) {
    TagModel model = models.get(clazz);
    if (null == model) {
      model = new TagModel(context, clazz);
      models.put(clazz, model);
    }
    return model;
  }

  public TagModel getAgent() {
    return get(Agent.class);
  }

  public TagModel getCheckbox() {
    return get(Checkbox.class);
  }

  public TagModel getCheckboxes() {
    return get(Checkboxes.class);
  }

  public TagModel getDate() {
    return get(Date.class);
  }

  public TagModel getDiv() {
    return get(Div.class);
  }

  public TagModel getEmail() {
    return get(Email.class);
  }

  public TagModel getFoot() {
    return get(Foot.class);
  }

  public TagModel getForm() {
    return get(Form.class);
  }

  public TagModel getGrid() {
    return get(Grid.class);
  }

  public TagModel getToolbar() {
    return get(Grid.Bar.class);
  }

  public TagModel getCol() {
    return get(Grid.Col.class);
  }

  public TagModel getRow() {
    return get(Grid.Row.class);
  }

  public TagModel getBoxcol() {
    return get(Grid.Boxcol.class);
  }

  public TagModel getHead() {
    return get(Head.class);
  }

  public TagModel getPassword() {
    return get(Password.class);
  }

  public TagModel getRadio() {
    return get(Radio.class);
  }

  public TagModel getRadios() {
    return get(Radios.class);
  }

  public TagModel getSelect() {
    return get(Select.class);
  }

  public TagModel getSelect2() {
    return get(Select2.class);
  }

  public TagModel getSubmit() {
    return get(Submit.class);
  }

  public TagModel getTextarea() {
    return get(Textarea.class);
  }

  public TagModel getTextfield() {
    return get(Textfield.class);
  }

  public TagModel getTextfields() {
    return get(Textfields.class);
  }

  public TagModel getButton() {
    return get(Button.class);
  }

  public TagModel getValidity() {
    return get(Validity.class);
  }
}
