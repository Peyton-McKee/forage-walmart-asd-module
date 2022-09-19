import csv
import sqlite3

#create a csv file containing the both shipping_data_1.csv and shipping_data_2.csv information

with open('data/shipping_data_1.csv', 'r') as shipping_data_1:
   reader = csv.reader(shipping_data_1)
   next(reader)
   consolidated_data = []
   prev_row = next(reader)
   product_quantity = 1
   # consolidate the data from shipping_data_1.csv so that it includes the quantity of each product. 
   for row in reader:
      if(row == prev_row):
         product_quantity += 1
      else:
         consolidated_data.append([prev_row[0], prev_row[1], prev_row[2], product_quantity])
         product_quantity = 1
      prev_row = row
   with open('data/shipping_data_2.csv', 'r') as shipping_data_2:
      reader2 = csv.reader(shipping_data_2)
      next(reader2)
      finished_data = []
      shipping_data_2_list = list(reader2)
      # associates the data from the consolidated data and the data from shipping_data_2.csv based off of the shipment identifier
      for row in consolidated_data:
         for row2 in shipping_data_2_list:
            if row[0] == row2[0]:
               finished_data.append([row2[1], row2[2], row[1], row[2], row[3], row2[3]])
               break
      #writes the finalized consolidated data into the new csv file 
      with open('data/shipping_data_3.csv', 'w') as shipping_data_3:
         writer = csv.writer(shipping_data_3)
         writer.writerow(['origin_warehouse', 'destination_store', 'product', 'on_time', 'product_quantity', 'driver_identifier'])
         for row in finished_data:
            writer.writerow(row)

#connect to the database, and create a table containing all the information on the shipping data
con = sqlite3.connect('shipment_database.db')
cur = con.cursor()
cur.execute("CREATE TABLE shipping_data(origin_warehouse, destination_store, product, on_time, product_quantity, driver_identifier)")

#add the shipping_data_3.csv to the database
with open('data/shipping_data_3.csv', 'r') as shipping_data_0:
   reader = csv.reader(shipping_data_0)
   next(reader)
   for row in reader:
      cur.execute("INSERT INTO shipping_data VALUES(?, ?, ?, ?, ?, ?)", row)
