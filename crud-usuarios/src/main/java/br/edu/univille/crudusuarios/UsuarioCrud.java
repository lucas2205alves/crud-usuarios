package br.edu.univille.crudusuarios;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@SpringComponent
@UIScope
public class UsuarioCrud extends VerticalLayout implements KeyNotifier {

    private final UsuarioRepository repository;


    private Usuario usuario;

    TextField nome = new TextField("Nome");
    PasswordField senha = new PasswordField("Senha");
    TextField cpf = new TextField("CPF");
    EmailField email = new EmailField("Email");
    TextField telefone = new TextField("Telefone");
    DatePicker dataNasc = new DatePicker("Data de Nascimento");
    RadioButtonGroup<String> genero = new RadioButtonGroup<>("Gênero");


    Button save = new Button("Salvar", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Deletar", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Usuario> binder = new Binder<>(Usuario.class);
    private ChangeHandler changeHandler;

    @Autowired
    public UsuarioCrud(UsuarioRepository repository) {
        this.repository = repository;

        genero.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        genero.setLabel("genero");
        genero.setItems("Masculino", "Feminino", "Prefiro não responder");

        FormLayout formLayout = new FormLayout();
        formLayout.add(nome, senha, cpf, email, telefone, dataNasc, genero, actions);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2));

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editUsuario(usuario));
        setVisible(false);

        add(formLayout);
    }

    void delete() {
        repository.delete(usuario);
        changeHandler.onChange();
    }

    void save() {
        repository.save(usuario);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editUsuario(Usuario c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            usuario = repository.findById(c.getId()).get();
        }
        else {
            usuario = c;
        }
        cancel.setVisible(persisted);
        binder.setBean(usuario);

        setVisible(true);
        nome.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}