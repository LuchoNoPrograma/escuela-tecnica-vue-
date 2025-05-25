export class UsuarioDTO {

  constructor(data:Partial<UsuarioDTO>) {
    Object.assign(this, data);
  }

  idUsuario?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreUsuario?: string|null;
  contrasenaHash?: string|null;
  estUsuario?: string|null;
  listaTareasAsignadas?: number[]|null;
  persona?: number|null;

}
