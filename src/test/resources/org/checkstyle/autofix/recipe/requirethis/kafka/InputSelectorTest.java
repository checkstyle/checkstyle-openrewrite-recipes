/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.requirethis.kafka;

public class InputSelectorTest {
    interface ChannelBuilder {
    }
    interface KafkaChannel {
    }
    
    void foo() {
        ChannelBuilder builder = new ChannelBuilder() {
            private int channelIndex = 0;
            
            KafkaChannel buildChannel() {
                return new KafkaChannel() {
                    private final int index = channelIndex++; // violation 'Reference to instance variable 'channelIndex' needs "this.".'
                };
            }
        };
    }
}
