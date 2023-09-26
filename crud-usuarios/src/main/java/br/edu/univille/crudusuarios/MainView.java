package br.edu.univille.crudusuarios;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final UsuarioRepository repo;
    private final UsuarioCrud editor;
    final Grid<Usuario> grid;
    private final Button adicionarUsuario;

    public MainView(UsuarioRepository repo, UsuarioCrud editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Usuario.class);
        this.adicionarUsuario = new Button("Novo Usuario", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(adicionarUsuario);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "nome","cpf", "email", "telefone", "dataFormatada", "genero");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);


        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editUsuario(e.getValue());
        });

        adicionarUsuario.addClickListener(e -> editor.editUsuario(new Usuario("","","","","","")));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listaUsuarios();
        });

        listaUsuarios();
    }


    private void listaUsuarios() {
        grid.setItems(repo.findAll());
    }

}