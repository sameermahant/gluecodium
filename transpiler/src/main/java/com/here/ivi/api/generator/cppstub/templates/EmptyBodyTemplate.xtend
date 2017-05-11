package com.here.ivi.api.generator.cppstub.templates

import com.here.ivi.api.model.cppmodel.CppMethod
import com.here.ivi.api.generator.common.templates.MethodBodyTemplate
import org.eclipse.xtend2.lib.StringConcatenation

class EmptyBodyTemplate implements MethodBodyTemplate {
  override StringConcatenation generate(CppMethod m) '''
  {
  }
  '''
}