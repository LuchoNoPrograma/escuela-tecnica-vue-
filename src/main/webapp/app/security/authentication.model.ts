export class AuthenticationRequest {

  constructor(data:Partial<AuthenticationRequest>) {
    Object.assign(this, data);
  }

  nombreUsuario?: string|null;
  password?: string|null;

}

export class AuthenticationResponse {
  accessToken?: string;
}
