import { AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

export class CustomValidators {

  static selectionRequired(invalidValue: any = ['', null, 0, -1]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        console.log(invalidValue , control.value);
        
      if (invalidValue === control.value) {
        return { selectionRequired: true };
      }
      return null;
    };
  }

}