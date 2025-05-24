import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  formLogin!: FormGroup;

  constructor(private fb : FormBuilder,
              private authService : AuthService,
              private router : Router) {

  }


  ngOnInit(): void {
    // Initialization logic here
    this.formLogin = this.fb.group({
      username : this.fb.control(''),
      password : this.fb.control('')
    })
  }

  handleLogin() {
    console.log("Tentative de connexion avec:", this.formLogin.value);
    this.authService.login(this.formLogin.value.username, this.formLogin.value.password).subscribe(
      {
        next: (data) => {
          console.log("Réponse du serveur:", data);
          this.authService.loadProfile(data);
          if (this.authService.isAuthenticated) {
            this.router.navigateByUrl("/admin/customers");
          } else {
            alert("Erreur d'authentification: token invalide");
          }
        },
        error: (err) => {
          console.log("Erreur d'authentification:", err);
          alert("Échec de connexion: " + (err.message || "Identifiants invalides"));
        }
      }
    );
  }
}
